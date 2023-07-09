import React, {useEffect, useState} from 'react';
import {Button, Space, Table, message, Input} from 'antd';
import {useDispatch, useSelector} from 'react-redux';
import ProductService from '../services/productService';
import CartService from '../services/cartService';

const ProductTable = () => {
    const allProducts = useSelector((state) => state.products.products);
    const dispatch = useDispatch();
    const userId = useSelector((state) => state.users.user.id);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
    const user = useSelector((state) => state.auth.user);

    useEffect(() => {
        getProducts();
    }, []);

    const getProducts = () => {
        ProductService.getProducts(dispatch);
    };

    const handleDeleteProduct = (productId) => {
        if (user.roles.includes("ROLE_ADMIN") && isLoggedIn) {
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
        } else {
            message.error("У вас недостаточно прав для удаления товара")
        }

    };

    const handleAddToCart = (product) => {
        if (isLoggedIn) {
            CartService.addToCart(userId, product.id, dispatch);
            message.success('Товар успешно добавлен в корзину');
        } else {
            message.error("Войдите в аккаунт, чтобы добавить товар в корзину")
        }
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
