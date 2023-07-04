import React, {useState, useEffect} from 'react'
import {RemoveFromCartButton} from './RemoveFromCartButton'
import {ChangeQuantityButton} from './ChangeQuantityButton'

export const Cart = ({cartItems, onRemoveFromCart, setCartItems}) => {
    const [editingItemId, setEditingItemId] = useState(null)
    const [totalPrice, setTotalPrice] = useState(0)

    useEffect(() => {
        let total = 0
        cartItems.forEach((item) => {
            total += item.product.price * item.quantity
        })
        setTotalPrice(total)
    }, [cartItems])

    const handleEditQuantity = (itemId) => {
        setEditingItemId(itemId)
    }

    const handleSaveQuantity = (itemId, editedQuantity) => {
        const updatedCartItems = cartItems.map((item) => {
            if (item.product.id === itemId) {

                if (item.quantity !== editedQuantity) {
                    return {
                        ...item,
                        quantity: item.quantity + (editedQuantity - item.quantity),
                    }
                }
            }
            return item
        })

        setCartItems(updatedCartItems)
        setEditingItemId(null)
    }


    const handleCancelEdit = () => {
        setEditingItemId(null)
    }

    if (cartItems.length === 0) {
        return (
            <div>
                <h2>Коризина с товарами</h2>
                <h3>Корзина пуста</h3>
            </div>
        )
    }

    return (
        <div>
            <h2>Коризина с товарами</h2>
            <ul>
                {cartItems.map((item) => (
                    <li key={item.product.id} className={'card'}>
                        <div>Название: {item.product.name}</div>
                        <div>Цена: {item.product.price} руб.</div>
                        <div>
                            Количество:{' '}
                            {editingItemId === item.product.id ? (
                                <ChangeQuantityButton
                                    itemId={item.product.id}
                                    currentQuantity={item.quantity}
                                    onSaveQuantity={handleSaveQuantity}
                                    onCancel={handleCancelEdit}
                                />
                            ) : (
                                <span>{item.quantity}</span>
                            )}
                            {editingItemId !== item.product.id && (
                                <button
                                    className={'button-change'}
                                    onClick={() => handleEditQuantity(item.product.id)}
                                >
                                    Изменить
                                </button>
                            )}
                        </div>
                        <div>Общая стоимость товара: {item.product.price * item.quantity} руб.</div>
                        <RemoveFromCartButton
                            productId={item.product.id}
                            onRemoveFromCart={onRemoveFromCart}
                        />
                    </li>
                ))}
            </ul>
            <h3>Общая стоимость к оплате: {totalPrice} руб.</h3>
        </div>
    )
}
