import {createSlice} from "@reduxjs/toolkit";

const productsCartSlice = createSlice({
    name: "cart",
    initialState: {
        cartItems: [],
    },
    reducers: {
        setCart: (state, action) => {
            state.cartItems = action.payload;
        }
    }

});

export const {setCart} = productsCartSlice.actions;
export default productsCartSlice.reducer;