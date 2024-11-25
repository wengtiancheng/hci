import {SOLUTION_MODULE} from "./_prefix";

import { axios } from '../utils/request';

export interface SolutionVO {
    id: number;
    name: string;
    imageUrl: string;
    totalPrice: number;
    description: string;
    saveNum: number;
    createTime: Date;
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
export const getAllSolutionNofilter = () => {
    return axios.get(`${SOLUTION_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}
// 获取所有解决方案（可以添加筛选参数）
export const getAllSolution = (filters: any) => {
    return axios.get(`${SOLUTION_MODULE}/all`, { params: filters })
        .then(res => {
            return res.data;  // 假设返回的数据是数组
        });
};


//上传一个装机方案
export const uploadSolution = (solution: SolutionVO) => {
    return axios.post(`${SOLUTION_MODULE}/save`, solution)
        .then(res => {
            return res
        })
}