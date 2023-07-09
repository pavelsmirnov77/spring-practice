import {Layout, Menu} from 'antd';
import React, {useState} from 'react';
import {
    ShoppingCartOutlined,
    InfoCircleFilled,
    UserSwitchOutlined,
    LoginOutlined,
    PlusCircleFilled, UnorderedListOutlined,
} from '@ant-design/icons';
import {Link, Route, Routes} from "react-router-dom";
import {NotFoundPage} from "./pages/NotFoundPage";
import RegistrationPage from "./pages/RegistrationPage";
import CartPage from "./pages/CartPage";
import LoginPage from "./pages/AuthPage";
import ProductsPage from "./pages/ProductsPage";
import CreateProductPage from "./pages/CreateProductPage";
import UserInfoPage from "./pages/UserInfoPage";

const {Content, Footer, Sider} = Layout;
const App = () => {
    const {Header, Content, Footer, Sider} = Layout;

    const [collapsed, setCollapsed] = useState(false);
    const items = [
        {
            key: '1',
            icon: <UserSwitchOutlined/>,
            label: 'Регистрация',
            url: '/registration'
        },
        {
            key: '2',
            icon: <LoginOutlined/>,
            label: 'Войти',
            url: '/auth'
        },
        {
            key: '3',
            icon: <UnorderedListOutlined/>,
            label: 'Список продуктов',
            url: '/',
        },
        {
            key: '4',
            icon: <PlusCircleFilled/>,
            label: 'Добавить товар',
            url: '/products/add',
        },
        {
            key: '5',
            icon: <ShoppingCartOutlined/>,
            label: 'Корзина',
            url: '/cart'
        },
        {
            key: '7',
            icon: <InfoCircleFilled/>,
            label: 'Информация о пользователе',
            url: '/user'
        },
    ];
    return (
        <Layout style={{minHeight: '100vh'}}>
            <Sider
                collapsible
                collapsed={collapsed}
                onCollapse={(value) => setCollapsed(value)}
                style={{overflowY: 'auto', height: '100vh', position: 'fixed', left: 0}}
            >
                <div className="demo-logo-vertical"/>
                <Menu theme="dark" defaultSelectedKeys={['3']} mode="inline">
                    {items.map((item) => (
                        <Menu.Item key={item.key} icon={item.icon}>
                            <Link to={item.url}>{item.label}</Link>
                        </Menu.Item>
                    ))}
                </Menu>
            </Sider>
            <Layout style={{marginLeft: collapsed ? 80 : 200}}>
                <Header className="app-header">
                    <h1 style={{color: 'white', display: 'flex', alignItems: 'center', marginTop: '8px'}}>Продуктовый
                        онлайн магазин</h1>
                    <div style={{marginLeft: 'auto', marginRight: '16px', display: 'flex', alignItems: 'center'}}>
                    </div>
                </Header>
                <Content style={{margin: "24px 16px 0",}}>
                    <Routes>
                        <Route path="/registration" element={<RegistrationPage/>}/>
                        <Route path="/auth" element={<LoginPage/>}/>
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
    );
};

export default App;