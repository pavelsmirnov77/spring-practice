import {createSlice} from "@reduxjs/toolkit";

export const cartSlice = createSlice({
    name: "carts",
    initialState: {
        cartItems: [],
    },
    reducers: {
        addToCart: (state, action) => {
            const newItem = action.payload;
            const existingItemIndex = state.cartItems.findIndex(cartItem => cartItem.id === newItem.id);

            if (existingItemIndex !== -1) {
                state.cartItems[existingItemIndex].quantity += 1;
            } else {
                state.cartItems.push({...newItem, quantity: 1});
            }
        },

        removeFromCart: (state, action) => {
            state.cartItems = state.cartItems.filter(product => product.id !== action.payload);
        },

        updateCartItemQuantity: (state, action) => {
            const {itemId, newQuantity} = action.payload;
            const itemToUpdate = state.cartItems.find(cartItem => cartItem.id === itemId);
            if (itemToUpdate) {
                itemToUpdate.quantity = newQuantity;
            }
        },

        clearCart: (state) => {
            state.cartItems = [];
        }
    },
});


export const {
    addToCart,
    removeFromCart,
    updateCartItemQuantity,
    clearCart
} = cartSlice.actions;

export default cartSlice.reducer;
