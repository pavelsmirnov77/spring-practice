import {Link, Route, Routes, useLocation} from 'react-router-dom';
import {Layout, Menu} from 'antd';
import {
    ShoppingCartOutlined,
    UserSwitchOutlined,
    LoginOutlined,
    PlusCircleFilled,
    UnorderedListOutlined,
} from '@ant-design/icons';
import {NotFoundPage} from './pages/NotFoundPage';
import RegistrationPage from './pages/RegistrationPage';
import CartPage from './pages/CartPage';
import LoginPage from './pages/AuthPage';
import ProductsPage from './pages/ProductsPage';
import CreateProductPage from './pages/CreateProductPage';
import UserInfoPage from './pages/UserInfoPage';
import authService from './services/auth.service';
import {logout} from './slices/authSlice';
import {useDispatch, useSelector} from 'react-redux';
import {useState} from "react";

const {Content, Footer, Sider, Header} = Layout;

const App = () => {
    const [collapsed, setCollapsed] = useState(false);
    const [selectedMenuKey, setSelectedMenuKey] = useState('3');
    const dispatch = useDispatch();

    const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
    const user = useSelector((state) => state.auth.user);

    const handleLogOut = () => {
        dispatch(logout(user));
        authService.logout();
    };

    const handleMenuSelect = (key) => {
        setSelectedMenuKey(key);
    };

    const location = useLocation();

    const handleSignUp = (userData) => {
        authService.register(userData);
    };

    return (
        <Layout style={{minHeight: '100vh'}}>
            <Header className="header">
                <div className="logo"/>
                <Menu theme="dark" mode="horizontal" selectedKeys={[selectedMenuKey]} onSelect={handleMenuSelect}>
                    <Menu.Item key="1" icon={<UnorderedListOutlined/>}>
                        <Link to="/">Список товаров</Link>
                    </Menu.Item>
                    {isLoggedIn && user !== null ? (
                        <>
                            <Menu.Item key="2" icon={<ShoppingCartOutlined/>}>
                                <Link to="/cart">Корзина</Link>
                            </Menu.Item>
                            <Menu.Item key="3" icon={<UserSwitchOutlined/>}>
                                <Link to="/user">Профиль</Link>
                            </Menu.Item>
                            <Menu.Item key="4" icon={<LoginOutlined/>} onClick={handleLogOut}>
                                <Link to="/api/auth/signin">Выйти</Link>
                            </Menu.Item>
                        </>
                    ) : (
                        <>
                            <Menu.Item key="2" icon={<UserSwitchOutlined/>}>
                                <Link to="/api/auth/signup">Регистрация</Link>
                            </Menu.Item>
                            <Menu.Item key="3" icon={<LoginOutlined/>}>
                                <Link to="/api/auth/signin">Авторизация</Link>
                            </Menu.Item>
                        </>
                    )}
                </Menu>
            </Header>
            <Layout>
                <Sider>
                    <div className="demo-logo-vertical"/>
                    <Menu theme="dark" mode="inline">
                        {location.pathname === "/" && (
                            <>
                                {isLoggedIn && user.roles.includes("ROLE_ADMIN") && user !== null ? (
                                    <>
                                        <Menu.Item key="2" icon={<PlusCircleFilled/>}>
                                            <Link to="/products/add">Добавление товаров</Link>
                                        </Menu.Item>
                                    </>
                                ) : null}
                            </>
                        )}
                    </Menu>
                </Sider>
                <Layout>
                    <Content style={{margin: '24px 16px 0'}}>
                        <Routes>
                            <Route path="/api/auth/signup" element={<RegistrationPage handleSignUp={handleSignUp}/>}/>
                            <Route path="/api/auth/signin" element={<LoginPage/>}/>
                            <Route path="/user" element={<UserInfoPage/>}/>
                            <Route path="/" element={<ProductsPage/>}/>
                            <Route path="/products/add" element={<CreateProductPage/>}/>
                            <Route path="/cart" element={<CartPage/>}/>
                            <Route path="*" element={<NotFoundPage/>}/>
                        </Routes>
                    </Content>
                    <Footer style={{textAlign: 'center'}}>Ant Design ©2023 Aliexpress "Овощи и фрукты"</Footer>
                </Layout>
            </Layout>
        </Layout>
    );
};

export default App;
