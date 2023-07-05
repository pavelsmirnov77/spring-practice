import {Button, Form, Input} from "antd";
import Password from "antd/es/input/Password";

const RegistrationForm = ({onSubmit}) => {
    const [form] = Form.useForm();

    const handleSubmit = () => {
        form.validateFields().then((values) => {
            form.resetFields();
            onSubmit(values);
        });
    };

    return (
        <Form form={form} layout="vertical">
            <Form.Item label="Имя" name="name" rules={[{required: true, message: 'Введите имя'}]}>
                <Input/>
            </Form.Item>
            <Form.Item label="Логин" name="login" rules={[{required: true, message: 'Введите логин'}]}>
                <Input/>
            </Form.Item>
            <Form.Item label="Email " name="email" rules={[{required: true, message: 'Введите email'}]}>
                <Input/>
            </Form.Item>
            <Form.Item label="Пароль " name="password" rules={[{required: true, message: 'Введите пароль'}]}>
                <Password/>
            </Form.Item>
            <Form.Item>
                <Button type="primary" onClick={handleSubmit}>
                    Зарегистрироваться
                </Button>
            </Form.Item>
        </Form>
    );
};

export default RegistrationForm;