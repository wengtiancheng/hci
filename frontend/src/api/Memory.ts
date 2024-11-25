import {MEMORY_MODULE} from "./_prefix";
import { axios } from '../utils/request';

//获取所有内存
export const getAllMemory = () => {
    return axios.get(`${MEMORY_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据内存Id获取内存
export const getMemoryById = (memoryId: number) => {
    return axios.get(`${MEMORY_MODULE}/${memoryId}`)
        .then(res => {
            return res
        })
}