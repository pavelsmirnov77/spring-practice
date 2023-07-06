import {Button, Space, Table} from 'antd';
import {useDispatch, useSelector} from 'react-redux';
import ProductService from "../services/productService";
import {useEffect} from "react";
import {addProductToCart} from "../services/cartService";

const ProductTable = () => {
    const products = useSelector((state) => state.products.products)
    const dispatch = useDispatch()

    useEffect(() => {
        getProducts();
    }, []);

    const getProducts = () => {
        ProductService.getProducts(dispatch);
    }

    const handleAddToCart = (productId) => {
        const product = products.find(item => item.id === productId);
        if (product) {
            dispatch(addProductToCart(product, dispatch));
        }
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
                <Space size="middle" onClick={() => ProductService.deleteProduct(product.id, dispatch)}>
                    <Button type="primary" style={{backgroundColor: 'grey'}}>Удалить</Button>
                </Space>
            ),
        },
        {
            title: '',
            key: 'action',
            render: (_, product) => (
                <Space size="middle" onClick={() => handleAddToCart(product.id)}>
                    <Button type="primary" style={{backgroundColor: '#001529'}}>Купить</Button>
                </Space>
            ),
        },
    ];

    return (
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
    );
};

export default ProductTable;
