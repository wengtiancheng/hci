import {GPU_MODULE} from "./_prefix";
import { axios } from '../utils/request';

//获取所有显卡
export const getAllGPU = () => {
    return axios.get(`${GPU_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据显卡Id获取显卡
export const getGPUById = (gpuId: number) => {
    return axios.get(`${GPU_MODULE}/${gpuId}`)
        .then(res => {
            return res
        })
}