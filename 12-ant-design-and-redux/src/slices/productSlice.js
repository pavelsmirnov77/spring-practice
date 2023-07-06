import {createSlice} from '@reduxjs/toolkit';

export const productSlice = createSlice({
    name: 'products',
    initialState: {
        products: [
            {
                id: 1,
                name: 'Яблоко',
                price: 60,
                quantity: 50,
            },
            {
                id: 2,
                name: 'Персик',
                price: 100000,
                quantity: 50,
            },
            {
                id: 3,
                name: 'Груша',
                price: 100,
                quantity: 50,
            },
            {
                id: 4,
                name: 'Банан',
                price: 120,
                quantity: 50,
            },
            {
                id: 5,
                name: 'Апельсин',
                price: 90,
                quantity: 50,
            },
        ],
        cart: [],
        filteredProducts: [],
    },
    reducers: {
        push: (state, action) => {
            const product = action.payload;
            product.id = Math.floor(Math.random() * 1_000_000);
            state.products = [action.payload, ...state.products];
        },

        remove: (state, action) => {
            state.products = state.products.filter((product) => product.id !== action.payload.id);
        },

        updateQuantity: (state, action) => {
            const {id, quantity} = action.payload;
            const product = state.products.find((product) => product.id === id);
            if (product) {
                product.quantity = quantity;
            }
        },
        searchProductByName: (state, action) => {
            const query = action.payload.toLowerCase();
            if (query == null) {
                state.filteredProducts = state.products;
            } else {
                state.filteredProducts = state.products.filter((product) =>
                    product.name.toLowerCase().includes(query))
                    state.products = state.filteredProducts;
            }
        },

    },
});

export const {push, remove, updateQuantity, searchProductByName} = productSlice.actions;

export default productSlice.reducer;
