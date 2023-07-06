import {createSlice} from "@reduxjs/toolkit";

export const cartSlice = createSlice({
    name: 'carts',
    initialState: {
        cartItems: [],
    },
    reducers: {

        clearCart: (state) => {
            state.cartItems = [];
        },

        setCartProducts: (state, action) => {
            state.cartItems = action.payload;
        }
    },
});

export const {
    clearCart,
    setCartProducts
} = cartSlice.actions;

export default cartSlice.reducer;
