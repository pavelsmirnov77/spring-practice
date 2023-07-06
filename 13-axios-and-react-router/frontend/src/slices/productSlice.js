import {createSlice} from '@reduxjs/toolkit';

export const productSlice = createSlice({
    name: 'products',
    initialState: {
        products: [],
        cart: [],
        filteredProducts: [],
    },
    reducers: {
        set: (state, action) => {
            state.products = action.payload;
        },
    },
});

export const {set} = productSlice.actions;

export default productSlice.reducer;

