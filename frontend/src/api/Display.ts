import {DISPLAY_MODULE} from "./_prefix";
import { axios } from '../utils/request';

//获取所有显示器
export const getAllDisplay = () => {
    return axios.get(`${DISPLAY_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据显示器Id获取显示器
export const getDisplayById = (displayId: number) => {
    return axios.get(`${DISPLAY_MODULE}/${displayId}`)
        .then(res => {
            return res
        })
}