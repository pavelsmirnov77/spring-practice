import React from 'react'

export const RemoveFromCartButton = ({ productId, onRemoveFromCart }) => {
    const handleClick = () => {
        onRemoveFromCart(productId)
    }

    return (
        <button className={"button-delete"} onClick={handleClick}>Удалить из корзины</button>
    )
}
