import {CPU_MODULE} from "./_prefix";
import { axios } from '../utils/request';

//获取所有CPU
export const getAllCPU = () => {
    return axios.get(`${CPU_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据CPUId获取CPU
export const getCPUById = (cpuId: number) => {
    return axios.get(`${CPU_MODULE}/${cpuId}`)
        .then(res => {
            return res
        })
}