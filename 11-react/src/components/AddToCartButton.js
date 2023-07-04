import React, {useState} from 'react'

export const AddToCartButton = ({product, onAddToCart}) => {
    const [quantity, setQuantity] = useState(1)

    const handleQuantityChange = (event) => {
        setQuantity(parseInt(event.target.value))
    }

    const handleAddToCart = () => {
        onAddToCart(product, quantity)
        setQuantity(1)
    }

    return (
        <div>
            <input className={"input-number"} type="number" value={quantity} min="1" onChange={handleQuantityChange} />
            <button className={"button-add"} onClick={handleAddToCart}>Добавить в корзину</button>
        </div>
    )
}
