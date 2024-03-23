import {Badge, Button, Card, InputNumber, message, Space, Table, Typography} from 'antd';
import { useSelector, useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';
import CartService from "../services/cartService";
import PaymentService from "../services/paymentService";
import userService from "../services/userService";
import {Link} from "react-router-dom";
const {Title, Text} = Typography;

const CartPage = () => {
    const userId = useSelector((state) => state.users.user.id);
    const cartItems = useSelector((state) => state.cart.cartItems);
    const dispatch = useDispatch();
    const products = useSelector((state) => state.products.products);

    useEffect(() => {
        const userId = localStorage.getItem('userId');
        if (userId) {
            userService.getUser(userId, dispatch);
        }
    }, [dispatch]);

    const currentDate = new Date().toLocaleString();
    const totalQuantity = cartItems.reduce((total, item) => total + item.quantity, 0);
    const totalCost = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);

    const handleRemoveProduct = (productId) => {
        CartService.deleteFromCart(userId, productId, dispatch)
            .then(() => {
                message.success("Товар успешно удален из корзины");
            })
            .catch((error) => {
                message.error("Ошибка при удалении товара из корзины");
                console.error(error);
            });
    };

    const handleUpdateQuantity = (productId, quantity) => {
        const newProductAmount = {
            quantity: quantity,
        };
        CartService.updateAmount(userId, productId, newProductAmount, dispatch)
            .then(() => {
                const updatedData = data.map((item) =>
                    item.itemId === productId ? {...item, quantity} : item
                );
                setData(updatedData);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    const handlePayment = () => {
        const payment = { cardNumber: 999999, userId: userId };
        PaymentService.pay(payment, dispatch)
            .then(() => {
                message.success('Оплата прошла успешно');
                CartService.clearCart(userId, dispatch);
            })
            .catch((error) => {
                message.error('Ошибка при оплате товара');
                console.error(error);
            });
    };

    const [editingItemId, setEditingItemId] = useState(null);
    const [data, setData] = useState([]);

    useEffect(() => {
        const newData = cartItems.map((item) => ({
            key: item.id,
            itemId: item.id,
            date: currentDate,
            name: item.name,
            quantity: item.quantity,
            price: item.price,
            editing: false,
        }));
        setData(newData);
    }, [cartItems, currentDate]);

    const handleEdit = (record) => {
        setEditingItemId(record.itemId);
    };

    const handleSave = (record) => {
        setEditingItemId(null);
        const newData = data.map((item) =>
            item.key === record.key ? {...item, quantity: record.quantity} : item
        );
        setData(newData);
        handleUpdateQuantity(record.itemId, record.quantity);
    };

    const handleQuantityChange = (userId, itemId, value) => {
        const newData = data.map((item) => {
            if (item.itemId === itemId) {
                const updatedItem = {...item, quantity: value};
                handleUpdateQuantity(itemId, value);
                return updatedItem;
            }
            return item;
        });
        setData(newData);
    };


    const handleCancel = () => {
        setEditingItemId(null);
    };

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
                            onChange={(value) => handleQuantityChange(userId, record.itemId, value)}
                        />
                    ) : (
                        text
                    ),
            },
            {
                title: 'Цена',
                dataIndex: 'price',
                key: 'price',
                render: (_, record) => {
                    const product = products.find((p) => p.id === record.itemId);
                    const itemPrice = product ? product.price : 0;
                    const totalPrice = itemPrice * record.quantity;

                    return totalPrice.toFixed(2);
                },
            },
            {
                title: 'Статус',
                key: 'state',
                render: (_, record) => {
                    const product = products.find((p) => p.id === record.itemId);
                    const status =
                        product && record.quantity <= product.quantity ? 'В наличии' : 'Не в наличии';

                    return <Badge status={status === 'В наличии' ? 'success' : 'error'} text={status} />;
                },
            },
            {
                title: 'Действия',
                dataIndex: 'operation',
                key: 'operation',
                render: (_, record) => (
                    <Space size="middle">
                        <Button
                            style={{ backgroundColor: 'grey', color: 'white' }}
                            onClick={() => handleRemoveProduct(record.itemId)}
                        >
                            Удалить
                        </Button>
                        {editingItemId === record.key ? (
                            <>
                                <Button
                                    style={{ backgroundColor: '#001529', color: 'white' }}
                                    onClick={() => handleSave(record)}
                                >
                                    Ок
                                </Button>
                                <Button
                                    style={{ backgroundColor: 'grey', color: 'white' }}
                                    onClick={() => handleCancel()}
                                >
                                    Отмена
                                </Button>
                            </>
                        ) : (
                            <Button
                                style={{ backgroundColor: '#001529', color: 'white' }}
                                onClick={() => handleEdit(record)}
                            >
                                Изменить
                            </Button>
                        )}
                    </Space>
                ),
            },
        ];

        return <Table columns={columns} dataSource={data} pagination={false} />;
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
                <Button style={{ backgroundColor: 'darkgreen', color: 'white' }} onClick={handlePayment}>
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

    if (!userId) {
        return (
            <Card>
                <div style={{textAlign: 'center', marginBottom: '16px'}}>
                    <Title level={4}>Вы не авторизированы</Title>
                    <div>
                        <Link to="/registration">
                            <Button type="primary" style={{marginRight: '8px'}}>Зарегистрироваться</Button>
                        </Link>
                        <Link to="/auth">
                            <Button>Войти</Button>
                        </Link>
                    </div>
                </div>
            </Card>
        );
    }

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

export default CartPage;
