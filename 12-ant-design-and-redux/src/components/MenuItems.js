import {Breadcrumb, Layout, Menu, Modal} from 'antd';
import {useState, useRef} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {
    UserOutlined,
    UserAddOutlined,
    ShoppingCartOutlined,
    PieChartOutlined,
    DesktopOutlined,
} from '@ant-design/icons';
import ProductsTable from './ProductsTable';
import SearchBar from './SearchBar';
import CreateProductForm from './CreateProductForm';
import Cart from './Cart';
import {clearCart} from '../slices/cartSlice';
import {addUser} from "../slices/clientSlice";
import RegistrationForm from "./RegistrationForm";

const {Header, Content, Footer, Sider} = Layout;

const MenuItems = () => {
    const clients = useSelector((state) => state.clients.clients);
    const dispatch = useDispatch();
    const cartTitleRef = useRef(null);

    const [collapsed, setCollapsed] = useState(false);
    const [selectedUser, setSelectedUser] = useState(clients.length > 0 ? clients[0] : null);
    const [showRegistrationForm, setShowRegistrationForm] = useState(false);

    const handleUserSelection = (user) => {
        setSelectedUser(user);
        dispatch(clearCart());
    };

    const handleAddClientClick = () => {
        setShowRegistrationForm(true);
    };

    const handleRegistrationFormSubmit = (values) => {
        const user = {
            id: Math.floor(Math.random() * 1_000_000),
            name: values.name,
            login: values.login,
            email: values.email,
            password: values.password
        };

        dispatch(addUser(user));
        setShowRegistrationForm(false);
    };

    const handleCartClick = () => {
        if (cartTitleRef.current) {
            cartTitleRef.current.scrollIntoView({behavior: 'smooth'});
        }
    };

    const clientItems = clients.map((client) => ({
        key: client.id.toString(),
        label: client.name,
        icon: <UserOutlined/>,
        onClick: () => handleUserSelection(client),
    }));

    const items = [
        {
            key: 'sub1',
            icon: <UserOutlined/>,
            children: clientItems,
            label: 'Пользователи',
        },
        {
            key: '4',
            icon: <UserAddOutlined/>,
            label: 'Добавить клиента',
            onClick: handleAddClientClick,
        },
        {
            key: '5',
            icon: <ShoppingCartOutlined/>,
            label: 'Корзина',
            onClick: handleCartClick,
        },
        {
            key: '6',
            icon: <PieChartOutlined/>,
            label: 'Статистика покупок',
        },
        {
            key: '7',
            icon: <DesktopOutlined/>,
            label: 'Мониторинг цен',
        },
    ];

    return (
        <Layout style={{minHeight: '200vh'}}>
            <Sider
                collapsible
                collapsed={collapsed}
                onCollapse={(value) => setCollapsed(value)}
                style={{overflowY: 'auto', height: '100vh', position: 'fixed', left: 0}}
            >
                <div className="demo-logo-vertical"/>
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" items={items}/>
            </Sider>
            <Layout style={{marginLeft: collapsed ? 80 : 200}}>
                <Header className="app-header">
                    <h1 style={{color: 'white'}}>Продуктовый онлайн магазин</h1>
                    <div style={{marginLeft: 'auto', marginRight: '16px', display: 'flex', alignItems: 'center'}}>
                        <SearchBar/>
                    </div>
                </Header>
                <Breadcrumb style={{margin: '16px 0', padding: '0 30px'}}>
                    <Breadcrumb.Item>Вход</Breadcrumb.Item>
                    {selectedUser && (
                        <>
                            <Breadcrumb.Item>Логин: {selectedUser.login}</Breadcrumb.Item>
                            <Breadcrumb.Item>Имя: {selectedUser.name}</Breadcrumb.Item>
                        </>
                    )}
                </Breadcrumb>
                <Content style={{padding: '0 250px'}}>
                    <div>
                        <h2 style={{padding: '0 350px'}}>Список товаров</h2>
                        <ProductsTable/>
                    </div>
                    <div style={{padding: '0 40px'}}>
                        <h2 style={{padding: '0 250px'}}>Добавление товара</h2>
                        <CreateProductForm/>
                    </div>
                    <div>
                        <h2 ref={cartTitleRef} style={{padding: '0 350px'}}>Корзина</h2>
                        <Cart/>
                    </div>
                </Content>
                <Footer style={{textAlign: 'center'}}>Ant Design ©2023 Aliexpress "Овощи и фрукты"</Footer>
            </Layout>
            <Modal
                visible={showRegistrationForm}
                title="Регистрация"
                onCancel={() => setShowRegistrationForm(false)}
                footer={null}
            >
                <RegistrationForm onSubmit={handleRegistrationFormSubmit}/>
            </Modal>
        </Layout>
    );
};

export default MenuItems;
