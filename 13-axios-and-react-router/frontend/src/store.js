import {configureStore} from '@reduxjs/toolkit'
import productsReducer from "./slices/productsSlice";
import cartReducer from "./slices/productsCartSlice";
import userReducer from "./slices/usersSlice";

export default configureStore({
    reducer: {
        products: productsReducer,
        cart: cartReducer,
        users: userReducer,
    },
})