import {Button, Space, Table, message} from 'antd';
import {useDispatch, useSelector} from 'react-redux';
import ProductService from '../services/productService';
import {useEffect} from "react";
import SearchBar from "../components/SearchBar";
import CartService from "../services/cartService";

const ProductTable = () => {
    const products = useSelector((state) => state.products.products);
    const dispatch = useDispatch();

    const userId = useSelector((state) => state.users.user.id);

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
        message.success("Товар успешно добавлен в корзину")
    };

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
            <SearchBar/>
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
