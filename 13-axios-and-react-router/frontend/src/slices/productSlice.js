import {createSlice} from '@reduxjs/toolkit';

export const productSlice = createSlice({
    name: 'products',
    initialState: {
        products: [],
        cart: [],
    },
    reducers: {
        set: (state, action) => {
            state.products = action.payload;
        },
        updateQuantity: (state, action) => {
            const { id, quantity } = action.payload;
            const product = state.products.find((product) => product.id === id);
            if (product) {
                product.quantity = quantity;
            }
        },
    },
});

export const {updateQuantity, set} = productSlice.actions;

export default productSlice.reducer;
