import {Button, Card, Form, Input} from 'antd';
import {UserOutlined, LockOutlined, LoginOutlined} from '@ant-design/icons';
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import authService from "../services/auth-service";
import {login} from "../slices/authSlice";

const AuthForm = () => {
    const [form] = Form.useForm();

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const onFinish = (values) => {
        authService.login(values).then((user) => {
            console.log(user)
            dispatch(login(user))
            navigate("/")
        })
    };

    return (
        <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh'}}>
            <Card title="Авторизация" style={{width: 500}}>
                <Form name="normal_login" form={form} layout="vertical" onFinish={onFinish}>
                    <Form.Item
                        name="login"
                        label="Логин"
                        rules={[{required: true, message: 'Введите логин'}]}
                    >
                        <Input prefix={<UserOutlined/>} placeholder="Логин"/>
                    </Form.Item>
                    <Form.Item
                        name="password"
                        label="Пароль"
                        rules={[{required: true, message: 'Введите пароль'}]}
                    >
                        <Input.Password prefix={<LockOutlined/>} placeholder="Пароль"/>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" icon={<LoginOutlined/>} htmlType="submit">
                            Войти
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};

export default AuthForm;
