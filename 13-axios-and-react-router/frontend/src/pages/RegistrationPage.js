import {Button, Card, Form, Input, message} from "antd";
import React from "react";
import {LockOutlined, MailOutlined, UserAddOutlined, UserOutlined} from "@ant-design/icons";
import UserService from "../services/userService";
import {useNavigate} from "react-router-dom";

const RegistrationPage = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    const onFinish = (values) => {
        UserService.register(values)
            .then(() => {
                message.success('Вы успешно зарегистрированы');
                navigate('/auth');
            })
            .catch((error) => {
                message.error('Ошибка при регистрации');
                console.error(error);
            });
    };

    return (
        <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh'}}>
            <Card title="Регистрация" style={{width: 400}}>
                <Form form={form} layout="vertical" name="registration" onFinish={onFinish}>
                    <Form.Item name="name" label="Имя" rules={[{required: true, message: 'Введите имя'}]}>
                        <Input prefix={<UserOutlined/>} placeholder="Имя"/>
                    </Form.Item>
                    <Form.Item name="login" label="Логин" rules={[{required: true, message: 'Введите логин'}]}>
                        <Input prefix={<UserOutlined/>} placeholder="Логин"/>
                    </Form.Item>
                    <Form.Item
                        name="email"
                        label="Email"
                        rules={[
                            {required: true, message: 'Введите email'},
                            {type: 'email', message: 'Некорректный email'},
                        ]}
                    >
                        <Input prefix={<MailOutlined/>} placeholder="Email"/>
                    </Form.Item>
                    <Form.Item name="password" label="Пароль" rules={[{required: true, message: 'Введите пароль'}]}>
                        <Input.Password prefix={<LockOutlined/>} placeholder="Пароль"/>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" icon={<UserAddOutlined/>} htmlType="submit" block>
                            Зарегистрироваться
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};

export default RegistrationPage;
