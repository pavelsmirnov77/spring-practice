import {configureStore} from '@reduxjs/toolkit'
import productsReducer from "./slices/productsSlice";
import cartReducer from "./slices/cartSlice";
import userReducer from "./slices/userSlice";
import authReducer from "./slices/authSlice";

export default configureStore({
    reducer: {
        products: productsReducer,
        cart: cartReducer,
        users: userReducer,
        auth: authReducer,
    },
})