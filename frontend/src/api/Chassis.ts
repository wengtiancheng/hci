import { axios } from '../utils/request';
import { CHASSIS_MODULE } from './_prefix';


//获取所有机箱
export const getAllChassis = () => {
    return axios.get(`${CHASSIS_MODULE}/all`, )
        .then(res => {
            return res
        })
}

//根据机箱Id获取机箱
export const getChassisById = (chassisId: number) => {
    return axios.get(`${CHASSIS_MODULE}/${chassisId}`)
        .then(res => {
            return res
        })
}

