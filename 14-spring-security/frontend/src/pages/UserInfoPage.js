import {Card, Avatar, Typography, Button} from 'antd';
import {useSelector, useDispatch} from 'react-redux';
import UserService from "../services/userService";
import {useEffect} from "react";
import {Link} from 'react-router-dom';
import AuthService from "../services/auth.service";

const {Title, Text} = Typography;

const ProfileCard = () => {
    const userId = useSelector((state) => state.users.user.id);
    const user = JSON.parse(localStorage.getItem("user"));
    const dispatch = useDispatch();

    useEffect(() => {
        UserService.getUser(userId, dispatch);
    }, []);

    const handleLogout = () => {
        AuthService.logout();
    };

    if (!userId) {
        return (
            <Card>
                <div style={{textAlign: 'center', marginBottom: '16px'}}>
                    <Title level={4}>Вы не авторизированы</Title>
                    <div>
                        <Link to="/api/auth/signup">
                            <Button type="primary" style={{marginRight: '8px'}}>Зарегистрироваться</Button>
                        </Link>
                        <Link to="/api/auth/signin">
                            <Button>Войти</Button>
                        </Link>
                    </div>
                </div>
            </Card>
        );
    }

    return (
        <Card>
            <div style={{display: 'flex', alignItems: 'center', justifyContent: 'center', marginBottom: '16px'}}>
                <Avatar size={128} shape="circle" src='https://mobile-comp.com/images/user.png'/>
            </div>

            <div style={{textAlign: 'center', marginBottom: '16px'}}>
                <Title level={2}>{user.name}</Title>
                <Text>{user.username}</Text>
            </div>

            <div style={{textAlign: 'center', marginBottom: '16px'}}>
                <Text style={{marginBottom: '8px'}}>Email: {user.email}</Text>
            </div>

            <div style={{textAlign: 'center', marginBottom: '16px'}}>
                <Text
                    style={{marginBottom: '8px'}}>Роль: {user.roles.includes("ROLE_ADMIN") ? 'администратор' : 'пользователь'}
                </Text>
            </div>

            <div style={{textAlign: 'center'}}>
                <Button type="primary" onClick={handleLogout}>Выйти</Button>
            </div>
        </Card>
    );
};

export default ProfileCard;
