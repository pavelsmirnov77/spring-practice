import {Button, Card, Form, Input} from "antd";
import React from "react";
import {LockOutlined, MailOutlined, UserAddOutlined, UserOutlined} from "@ant-design/icons";
import authService from "../services/auth-service";

const RegistrationPage = () => {
    const [form] = Form.useForm();
    const onFinish = (values) => {
        authService.register(values)
    };

    return (
        <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh'}}>
            <Card title="Регистрация" style={{width: 400}}>
                <Form form={form} layout="vertical" name="register"
                      onFinish={onFinish}>
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