import {createSlice} from "@reduxjs/toolkit";

export const cartSlice = createSlice({
    name: "carts",
    initialState: {
        items: [],
    },
    reducers: {
        addToCart: (state, action) => {
            const newItem = action.payload;
            const existingItemIndex = state.items.findIndex(item => item.id === newItem.id);

            if (existingItemIndex !== -1) {
                state.items[existingItemIndex].quantity += 1;
            } else {
                state.items.push({...newItem, quantity: 1});
            }
        },

        removeFromCart: (state, action) => {
            state.items = state.items.filter(product => product.id !== action.payload);
        },

        updateCartItemQuantity: (state, action) => {
            const {itemId, newQuantity} = action.payload;
            const itemToUpdate = state.items.find(item => item.id === itemId);
            if (itemToUpdate) {
                itemToUpdate.quantity = newQuantity;
            }
        },

        clearCart: (state) => {
            state.items = [];
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
