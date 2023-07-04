import React, {useState} from 'react'
import {RemoveFromCartButton} from './RemoveFromCartButton'

export const Cart = ({cartItems, onRemoveFromCart, setCartItems}) => {
    const [editingItemId, setEditingItemId] = useState(null)
    const [editedQuantity, setEditedQuantity] = useState(0);

    const handleEditQuantity = (itemId, currentQuantity) => {
        setEditingItemId(itemId)
        setEditedQuantity(currentQuantity)
    }

    const handleSaveQuantity = (itemId) => {
        const updatedCartItems = cartItems.map((item) => {
            if (item.product.id === itemId) {
                return {
                    ...item,
                    quantity: editedQuantity,
                };
            }
            return item;
        })

        setCartItems(updatedCartItems)

        setEditingItemId(null)
        setEditedQuantity(0);
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
                        <div>Цена: {item.product.price}</div>
                        <div>
                            Количество:{' '}
                            {editingItemId === item.product.id ? (
                                <input
                                    className={"small-input"}
                                    type="number"
                                    value={editedQuantity}
                                    onChange={(e) =>
                                        setEditedQuantity(e.target.value)}
                                />
                            ) : (
                                <span>{item.quantity}</span>
                            )}
                            {editingItemId === item.product.id ? (
                                <>
                                    <button className={"button-change"} onClick={() =>
                                        handleSaveQuantity(item.product.id)}>OK</button>
                                    <button className={"button-change"} onClick={() =>
                                        setEditingItemId(null)}>Отмена</button>
                                </>
                            ) : (
                                <button className={"button-change"} onClick={() => handleEditQuantity(item.product.id, item.quantity)}>
                                    Изменить
                                </button>
                            )}
                        </div>
                        <div>Общая стоимость товара: {item.product.price * item.quantity}</div>
                        <RemoveFromCartButton productId={item.product.id} onRemoveFromCart={onRemoveFromCart} />
                    </li>
                ))}
            </ul>
        </div>
    );
};
