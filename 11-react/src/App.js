import React, {useState} from 'react'
import {User} from './components/User'
import {Products} from './components/Products'
import {Cart} from './components/Cart'
import './App.css'
import {Payment} from "./components/Payment"

const App = () => {
    const [currentUser, setCurrentUser] = useState(null)
    const [cartItems, setCartItems] = useState([])

    const handleUserChange = (user) => {
        setCurrentUser(user)
        setCartItems([])
    }

    const handleAddToCart = (product, quantity) => {
        const existingItem = cartItems.find((item) =>
            item.product.id === product.id)

        if (existingItem) {
            const updatedItem = {
                ...existingItem,
                quantity: existingItem.quantity + quantity,
            }
            const updatedItems = cartItems.map((item) =>
                (item.product.id === product.id ? updatedItem : item))
            setCartItems(updatedItems)
        } else {
            const newItem = {
                product,
                quantity,
            }
            setCartItems([...cartItems, newItem])
        }
    }

    const handlePayment = (amount) => {
        if (!currentUser) {
            alert('Пожалуйста, выберите пользователя.')
            return
        }

        if (cartItems.length === 0) {
            alert('Корзина пуста.')
            return
        }

        const totalAmount = cartItems.reduce(
            (total, item) => total + item.product.price * item.quantity,
            0
        )

        if (currentUser.balance < totalAmount) {
            alert('Оплата не прошла: недостаточно средств на балансе.')
        } else {
            const isAvailable = cartItems.every((item) =>
                item.quantity <= item.product.quantity)

            if (isAvailable) {
                alert('Оплата прошла успешно.')
                setCartItems([])
            } else {
                alert('Оплата не прошла: количество товара превышает доступное количество на складе.')
            }
        }
    }

    const handleRemoveFromCart = (productId) => {
        const updatedItems = cartItems.filter((item) =>
            item.product.id !== productId)
        setCartItems(updatedItems)
    }

    return (
        <div>
            <User
                currentUser={currentUser}
                onUserChange={handleUserChange}
            />
            <Products
                onAddToCart={handleAddToCart}
            />
            <Cart
                cartItems={cartItems}
                setCartItems={setCartItems}
                onRemoveFromCart={handleRemoveFromCart}
            />
            <Payment
                currentUser={currentUser}
                cartItems={cartItems}
                onPayment={handlePayment}
            />
        </div>
    )
}

export default App
