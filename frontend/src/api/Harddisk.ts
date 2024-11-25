import {HARD_DISK_MODULE} from "./_prefix";
import { axios } from '../utils/request';


//获取所有硬盘
export const getAllHarddisk = () => {
    return axios.get(`${HARD_DISK_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据硬盘Id获取硬盘
export const getHarddiskById = (harddiskId: number) => {
    return axios.get(`${HARD_DISK_MODULE}/${harddiskId}`)
        .then(res => {
            return res
        })
}