import {SOLUTION_MODULE} from "./_prefix";

import { axios } from '../utils/request';

export interface SolutionVO {
    id: number;
    name: string;
    imageUrl: string;
    totalPrice: number;
    description: string;
    saveNum: number;
    cpuId: number;
    motherboardId: number;
    gpuId: number;
    memoryId: number;
    harddiskId: number;
    powersupplyId: number;
    coolingId: number;
    chassisId: number;
    displayId: number;
}


//获取所有解决方案
export const getAllSolution = () => {
    return axios.get(`${SOLUTION_MODULE}/all`, )
        .then(res => {
            return res
        })
}


//上传一个装机方案
export const uploadSolution = (solution: SolutionVO) => {
    return axios.post(`${SOLUTION_MODULE}/save`, solution)
        .then(res => {
            return res
        })
}