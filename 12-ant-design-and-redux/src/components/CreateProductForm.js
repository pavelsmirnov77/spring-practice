import {Button, Form, Input, InputNumber, Select} from 'antd';
import {useDispatch} from "react-redux";
import {push} from "../slices/productSlice";

const CreateProductForm = () => {
    const {Option} = Select;
    const layout = {
        labelCol: {
            span: 8,
        },
        wrapperCol: {
            span: 16,
        },
    };
    const tailLayout = {
        wrapperCol: {
            offset: 8,
            span: 16,
        },
    };

    const dispatch = useDispatch();
    const [form] = Form.useForm();

    const onFinish = (values) => {
        dispatch(push(values));
    };

    const onReset = () => {
        form.resetFields();
    };

    return (
        <Form
            {...layout}
            form={form}
            name="control-hooks"
            onFinish={onFinish}
            style={{
                maxWidth: 600,
                padding: '20px 50px',
            }}
        >
            <Form.Item
                name="name"
                label="Название"
                rules={[
                    {
                        required: true,
                    },
                ]}
            >
                <Input/>
            </Form.Item>

            <Form.Item
                name="price"
                label="Цена"
                rules={[
                    {
                        required: true,
                    },
                ]}
            >
                <Input/>
            </Form.Item>

            <Form.Item
                name="quantity"
                label="Количество"
                rules={[
                    {
                        required: true,
                    },
                ]}
            >
                <InputNumber/>
            </Form.Item>

            <Form.Item {...tailLayout}>
                <Button type="primary" style={{backgroundColor: '#001529'}} htmlType="submit">
                    Создать
                </Button>
                <Button type="primary" style={{marginLeft: '5px', backgroundColor: 'grey'}} htmlType="button" onClick={onReset}>
                    Очистить
                </Button>
            </Form.Item>
        </Form>
    );
};

export default CreateProductForm;
