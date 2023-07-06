import {AutoComplete, Input} from 'antd';
import {useState} from 'react';
import {useSelector} from "react-redux";

const Search = () => {
    const products = useSelector((state) => state.products.products)
    const [options, setOptions] = useState([]);
    const handleSearch = (value) => {
        setOptions(value ? searchResult(value) : []);
    };
    const onSelect = (value) => {
        console.log('onSelect', value);
    };

    const searchResult = (query) => {
        return products
            .filter(product => product.name.toLowerCase().includes(query.toLowerCase()))
            .map(product => {
                return {
                    value: product.key,
                    label: <div key={product.key}>{product.name}</div>
                }
            })
    }

    return (
        <AutoComplete
            popupMatchSelectWidth={252}
            style={{
                width: 300,
            }}
            options={options}
            onSelect={onSelect}
            onSearch={handleSearch}
        >
            <Input.Search size="large" placeholder="Введите название продукта" enterButton/>
        </AutoComplete>
    );
};
export default Search;