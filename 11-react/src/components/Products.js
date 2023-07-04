import React, {useState} from 'react'
import {AddToCartButton} from './AddToCartButton'
import {AddProductForm} from './AddProductForm'
import {SearchBar} from './SearchBar'
import {RemoveProductButton} from "./RemoveProductButton"

export const Products = ({onAddToCart}) => {
    const [products, setProducts] = useState([
        {
            id: 1,
            name: 'Яблоко',
            price: 10.0,
            quantity: 20,
        },
        {
            id: 2,
            name: 'Банан',
            price: 20.0,
            quantity: 20,
        },
        {
            id: 3,
            name: 'Персик',
            price: 10000000.0,
            quantity: 20,
        },
    ])

    const [filteredProducts, setFilteredProducts] = useState(products)

    const handleAddProduct = (newProduct) => {
        const newId = products.length + 1
        const updatedProducts = [...products, {...newProduct, id: newId}]
        setProducts(updatedProducts)
    }

    const handleSearch = (searchTerm) => {
        const filtered = products.filter((product) =>
            product.name.toLowerCase().includes(searchTerm.toLowerCase())
        )
        setFilteredProducts(filtered)
    }

    const handleRemoveProduct = (productId) => {
        const updatedProducts = products.filter((product) =>
            product.id !== productId)
        setProducts(updatedProducts)
        setFilteredProducts(updatedProducts)
    }

    return (
        <div>
            <h2>Список продуктов</h2>
            <SearchBar onSearch={handleSearch}/>
            <h2>Добавить товар</h2>
            <AddProductForm onAddProduct={handleAddProduct}/>
            <ul>
                {filteredProducts.map((product) => (
                    <div key={product.id} className={"card"}>
                        <div className={"card-text"}>Название: {product.name}</div>
                        <div className={"card-text"}>Цена: {product.price} руб.</div>
                        <div className={"card-text"}>Количество: {product.quantity}</div>
                        <div align={"center"}>
                            <AddToCartButton product={product} onAddToCart={onAddToCart}/>
                            <RemoveProductButton onRemove={() => handleRemoveProduct(product.id)}/>
                        </div>
                    </div>
                ))}
            </ul>
        </div>
    )
}
