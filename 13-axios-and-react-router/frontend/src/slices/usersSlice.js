import {createSlice} from "@reduxjs/toolkit";

const usersSlice = createSlice({
    name: "users",
    initialState: {
        user: []
    },
    reducers: {
        set: (state, action) => {
            state.user = action.payload;
        },
    },
});

export const {set} = usersSlice.actions;
export default usersSlice.reducer;