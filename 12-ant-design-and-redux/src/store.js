import {configureStore} from '@reduxjs/toolkit'
import productsReducer from "./slices/productSlice";
import clientsReducer from "./slices/clientSlice";
import cartReducer from "./slices/cartSlice";

export default configureStore({
    reducer: {
        products: productsReducer,
        clients: clientsReducer,
        carts: cartReducer
    },
})
