import React, {useState} from 'react'

export const AddProductForm = ({onAddProduct}) => {
    const [name, setName] = useState('')
    const [price, setPrice] = useState('')
    const [quantity, setQuantity] = useState('')

    const handleNameChange = (event) => {
        setName(event.target.value)
    }

    const handlePriceChange = (event) => {
        setPrice(event.target.value)
    }

    const handleQuantityChange = (event) => {
        setQuantity(event.target.value)
    }

    const handleAddProduct = () => {
        const newProduct = {
            name: name,
            price: parseFloat(price),
            quantity: parseInt(quantity),
        }

        onAddProduct(newProduct)
        setName('')
        setPrice('')
        setQuantity('')
    }

    return (
        <div>
            <input
                className={"input-text"}
                type="text"
                placeholder="Название"
                value={name}
                onChange={handleNameChange}
            />
            <input
                className={"input-number"}
                type="number"
                placeholder="Цена"
                value={price}
                onChange={handlePriceChange}
            />
            <input
                className={"input-number"}
                type="number"
                placeholder="Количество"
                value={quantity}
                onChange={handleQuantityChange}
            />
            <button
                className={"button-small"}
                onClick={handleAddProduct}>
                Добавить товар
            </button>
        </div>
    )
}
