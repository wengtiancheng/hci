import AllSolutions from "../views/AllSolutions.vue";
import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {
        path: '/solution',
        name: 'AllSolutions',
        component: AllSolutions
    },
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

export default router

