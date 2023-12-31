import { AutoComplete, Input } from 'antd';
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { searchProductByName } from '../slices/productSlice';

const Search = () => {
    const allProducts = useSelector((state) => state.products.products);
    const dispatch = useDispatch();
    const [options, setOptions] = useState([]);
    const [searchResults, setSearchResults] = useState([]);

    const searchResult = (query) => {
        let searchProducts;
        if (query) {
            searchProducts = allProducts.filter((product) =>
                product.name.toLowerCase().includes(query.toLowerCase())
            );
        } else {
            searchProducts = allProducts;
        }

        setSearchResults(searchProducts);

        const productOptions = searchProducts.map((product) => ({
            value: product.name,
            label: <div key={product.id}>{product.name}</div>,
        }));

        setOptions(productOptions);
    };

    const handleSearch = (value) => {
        dispatch(searchProductByName(value.toString()));
    };

    return (
        <AutoComplete
            popupMatchSelectWidth={252}
            style={{
                width: 300,
            }}
            options={options}
            onSearch={searchResult}
        >
            <Input.Search
                size="large"
                placeholder="Введите название"
                enterButton
                onSearch={handleSearch}
            />
        </AutoComplete>
    );
};

export default Search;
