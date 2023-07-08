import {createSlice} from "@reduxjs/toolkit";

export const productsSlice = createSlice({
    name: 'products',
    initialState: {
        products: [],
    },
    reducers: {
        set: (state, action) => {
            state.products = action.payload;
        }
    },
})

export const {set} = productsSlice.actions;

export default productsSlice.reducer;