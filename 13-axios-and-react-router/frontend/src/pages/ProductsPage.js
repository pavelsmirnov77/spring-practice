import React, {useEffect, useState} from 'react';
import {Button, Space, Table, message, Input, Card, Typography} from 'antd';
import {useDispatch, useSelector} from 'react-redux';
import ProductService from '../services/productService';
import CartService from '../services/cartService';
import {Link} from "react-router-dom";
const {Title, Text} = Typography;

const ProductTable = () => {
    const allProducts = useSelector((state) => state.products.products);
    const dispatch = useDispatch();
    const userId = useSelector((state) => state.users.user.id);
    const [filteredProducts, setFilteredProducts] = useState([]);

    useEffect(() => {
        getProducts();
    }, []);

    const getProducts = () => {
        ProductService.getProducts(dispatch);
    };

    const handleDeleteProduct = (productId) => {
        ProductService.deleteProduct(productId, dispatch)
            .then(() => {
                message.success({
                    content: 'Продукт успешно удален',
                    duration: 3,
                });
            })
            .catch((error) => {
                message.error({
                    content: 'Ошибка при удалении продукта',
                    duration: 3,
                });
                console.error(error);
            });
    };

    const handleAddToCart = (product) => {
        CartService.addToCart(userId, product.id, dispatch);
        message.success('Товар успешно добавлен в корзину');
    };

    const handleSearch = (value) => {
        const searchProducts = allProducts.filter((product) =>
            product.name.toLowerCase().includes(value.toLowerCase())
        );
        setFilteredProducts(searchProducts);
    };

    const products = filteredProducts.length > 0 ? filteredProducts : allProducts;

    const columns = [
        {
            title: 'Название',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Цена',
            dataIndex: 'price',
            key: 'price',
        },
        {
            title: 'Количество',
            dataIndex: 'quantity',
            key: 'quantity',
        },
        {
            title: '',
            key: 'action',
            render: (_, product) => (
                <Space size="middle" onClick={() => handleDeleteProduct(product.id)}>
                    <Button type="primary" style={{backgroundColor: 'grey'}}>
                        Удалить
                    </Button>
                </Space>
            ),
        },
        {
            title: '',
            key: 'action',
            render: (_, product) => (
                <Space size="middle">
                    <Button
                        type="primary"
                        style={{backgroundColor: '#001529'}}
                        onClick={() => handleAddToCart(product)}
                    >
                        Купить
                    </Button>
                </Space>
            ),
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
        <div>
            <Input.Search
                placeholder="Поиск по названию"
                enterButton
                allowClear
                onSearch={handleSearch}
                style={{marginBottom: '16px'}}
            />
            <Table
                rowKey="id"
                columns={columns}
                dataSource={products}
                pagination={{
                    pageSize: 5,
                }}
                scroll={{
                    y: 400,
                }}
            />
        </div>
    );
};

export default ProductTable;
