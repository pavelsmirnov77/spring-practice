import {Badge, Button, InputNumber, message, Space, Table} from 'antd';
import {useSelector, useDispatch} from 'react-redux';
import {removeFromCart, updateCartItemQuantity, makePayment, getCartProducts} from '../services/cartService';
import {clearCart} from '../slices/cartSlice'
import {useState, useEffect} from 'react';

const Cart = () => {
    const products = useSelector(state => state.products.products);
    const cartItems = useSelector((state) => state.carts.cartItems);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getCartProducts());
    }, [dispatch]);

    const currentDate = new Date().toLocaleString();
    const totalQuantity = cartItems.reduce((total, item) => total + item.quantity, 0);
    const totalCost = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);

    const handleQuantityChange = (itemId, newQuantity) => {
        dispatch(updateCartItemQuantity(itemId, newQuantity, dispatch));
    };

    const handlePayment = () => {
        const insufficientQuantityItems = cartItems.filter(
            (cartItem) =>
                cartItem.quantity >
                (products.find((product) => product.id === cartItem.id)?.quantity || 0)
        );

        if (cartItems.length === 0) {
            message.error("Корзина пустая.");
        } else {
            message.error(
                "Ошибка при оплате. Количество товаров в корзине превышает доступное количество."
            );
        }

        dispatch(makePayment())
            .then(() => {
                dispatch(clearCart());
                message.success("Оплата прошла успешно!");
            })
            .catch((error) => {
                message.error("Ошибка при оплате.");
                console.error(error);
            });
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
                title: 'Кол-во',
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
                title: 'Цена',
                dataIndex: 'cost',
                key: 'cost',
            },
            {
                title: 'Статус',
                key: 'state',
                render: (_, record) => {
                    const product = products.find((p) => p.id === record.itemId);
                    const status = product && record.quantity <= product.quantity ? 'В наличии' : 'Не в наличии';

                    return <Badge status={status === 'В наличии' ? 'success' : 'error'} text={status}/>;

                },
            },
            {
                title: 'Действия',
                dataIndex: 'operation',
                key: 'operation',
                render: (_, record) => (
                    <Space size="middle">
                        <Button
                            style={{backgroundColor: 'grey', color: 'white'}}
                            onClick={() => dispatch(removeFromCart(record.itemId))}
                        >
                            Удалить
                        </Button>
                        {editingItemId === record.itemId ? (
                            <>
                                <Button
                                    style={{backgroundColor: '#001529', color: 'white'}}
                                    onClick={() => handleSave(record)}
                                >
                                    Ок
                                </Button>
                                <Button
                                    style={{backgroundColor: 'grey', color: 'white'}}
                                    onClick={() => handleCancel()}
                                >
                                    Отмена
                                </Button>
                            </>
                        ) : (
                            <Button
                                style={{backgroundColor: '#001529', color: 'white'}}
                                onClick={() => handleEdit(record)}
                            >
                                Изменить
                            </Button>
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
            render: () => (
                <Button style={{backgroundColor: 'darkgreen', color: 'white'}} onClick={handlePayment}>
                    Оплатить
                </Button>
            ),
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
