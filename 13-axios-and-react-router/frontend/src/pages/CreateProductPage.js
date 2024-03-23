import {Button, Card, Form, Input, InputNumber, message, Typography} from 'antd';
import {useDispatch, useSelector} from 'react-redux';
import ProductService from '../services/productService';
import {Link} from "react-router-dom";
const {Title, Text} = Typography;

const CreateProductPage = () => {
    const dispatch = useDispatch();
    const [form] = Form.useForm();
    const userId = useSelector((state) => state.users.user.id);

    const onFinish = (values) => {
        ProductService.createProduct(values, dispatch)
            .then(() => {
                message.success({
                    content: 'Продукт успешно создан',
                    duration: 2,
                });
                form.resetFields();
            })
            .catch((error) => {
                message.error({
                    content: 'Ошибка при создании продукта',
                    duration: 2,
                });
                console.error(error);
            });
    };

    const onReset = () => {
        form.resetFields();
    };

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
        <Card title="Добавить товар">
            <Form form={form} layout="vertical" onFinish={onFinish}>
                <Form.Item name="name" label="Название" rules={[{
                    required: true,
                    message: 'Введите название товара'
                }]}>
                    <Input/>
                </Form.Item>

                <Form.Item name="price" label="Цена" min={1} rules={[{
                    required: true,
                    message: 'Введите цену товара'
                }]}>
                    <InputNumber/>
                </Form.Item>

                <Form.Item name="quantity" label="Количество" min={1} rules={[{
                    required: true,
                    message: 'Введите количество товара'
                }]}>
                    <InputNumber/>
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        Создать
                    </Button>
                    <Button style={{marginLeft: '5px'}} onClick={onReset}>
                        Очистить
                    </Button>
                </Form.Item>
            </Form>
        </Card>
    );
};

export default CreateProductPage;
