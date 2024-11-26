import AllSolutions from "../views/AllSolutions.vue";
import SolutionDetail from "../views/SolutionDetail.vue";
import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {
        path: '/solution',
        name: 'AllSolutions',
        component: AllSolutions
    },
    {
        path: '/solution/:id',
        name: 'SolutionDetail',
        component: SolutionDetail
    },
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

export default router

