import {Badge, InputNumber, message, Space, Table} from 'antd';
import {useSelector, useDispatch} from 'react-redux';
import {clearCart, removeFromCart, updateCartItemQuantity} from '../slices/cartSlice';
import {useState} from 'react';

const Cart = () => {
    const products = useSelector(state => state.products.products);
    const cartItems = useSelector((state) => state.cart.items);
    const dispatch = useDispatch();

    const currentDate = new Date().toLocaleString();
    const totalQuantity = cartItems.reduce((total, item) => total + item.quantity, 0);
    const totalCost = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);

    const handleQuantityChange = (itemId, newQuantity) => {
        dispatch(updateCartItemQuantity({itemId, newQuantity}));
    };

    const handlePayment = () => {
        if (cartItems.length === 0) {
            message.error('Корзина пуста. Невозможно произвести оплату.');
            return;
        }

        const paymentSuccessful = cartItems.every((cartItem) => {
            const product = products.find((p) => p.id === cartItem.id);

            if (product && cartItem.quantity <= product.quantity) {
                return true;
            }

            return false;
        });

        if (paymentSuccessful) {
            dispatch(clearCart());
            message.success('Оплата прошла успешно!');
        } else {
            message.error('Ошибка оплаты. Некоторые товары в корзине превышают доступное количество.');
        }
    };

    const [editingItemId, setEditingItemId] = useState(null);

    const expandedRowRender = () => {
        const columns = [
            {
                title: 'Дата',
                dataIndex: 'date',
                key: 'date',
            },
            {
                title: 'Название',
                dataIndex: 'name',
                key: 'name',
            },
            {
                title: 'Количество',
                dataIndex: 'quantity',
                key: 'quantity',
                render: (text, record) =>
                    editingItemId === record.key ? (
                        <InputNumber
                            min={1}
                            defaultValue={text}
                            onChange={(value) => handleQuantityChange(record.key, value)}
                        />
                    ) : (
                        text
                    ),
            },
            {
                title: 'Стоимость',
                dataIndex: 'cost',
                key: 'cost',
            },
            {
                title: 'Статус',
                key: 'state',
                render: (_, record) => {
                    const product = products.find((p) => p.id === record.itemId);
                    const status = product && record.quantity <= product.quantity ? 'В наличии' : 'Не в наличии';

                    return <Badge status={status === 'В наличии' ? 'success' : 'error'} text={status} />;
                },
            },
            {
                title: 'Действия',
                dataIndex: 'operation',
                key: 'operation',
                render: (_, record) => (
                    <Space size="middle">
                        <a onClick={() => dispatch(removeFromCart(record.itemId))}>Удалить</a>
                        {editingItemId === record.itemId ? (
                            <>
                                <a onClick={() => handleSave(record)}>Ок</a>
                                <a onClick={() => handleCancel()}>Отмена</a>
                            </>
                        ) : (
                            <a onClick={() => handleEdit(record)}>Изменить</a>
                        )}
                    </Space>
                ),

            },
        ];

        const data = cartItems.map((item) => ({
            key: item.id,
            itemId: item.id,
            date: currentDate,
            name: item.name,
            quantity: item.quantity,
            cost: item.price,
            editing: false,
        }));

        const handleEdit = (record) => {
            setEditingItemId(record.itemId);
        };

        const handleSave = (record) => {
            setEditingItemId(null);
            handleQuantityChange(record.itemId, record.quantity);
        };

        const handleCancel = () => {
            setEditingItemId(null);
        };

        return <Table columns={columns} dataSource={data} pagination={false}/>;
    };

    const columns = [
        {
            title: 'Дата',
            dataIndex: 'datePayment',
            key: 'datePayment',
        },
        {
            title: 'Общее количество товара',
            dataIndex: 'totalQuantity',
            key: 'totalQuantity',
        },
        {
            title: 'Общая стоимость товара',
            dataIndex: 'totalCost',
            key: 'totalCost',
        },
        {
            title: 'Оплата',
            key: 'operation',
            render: () => <a onClick={handlePayment}>Оплатить</a>,
        },
    ];

    const dataHead = [
        {
            key: '0',
            datePayment: currentDate,
            totalQuantity,
            totalCost,
        },
    ];

    return (
        <>
            <Table
                columns={columns}
                expandable={{
                    expandedRowRender,
                    defaultExpandedRowKeys: ['0'],
                }}
                dataSource={dataHead}
            />
        </>
    );
};

export default Cart;
