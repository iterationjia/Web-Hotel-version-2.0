import { axios } from '@/utils/request'
const api = {
    dbPre: '/api/db'
}
export function updateDatabaseAPI() {
    return axios({
        url: `${api.dbPre}/updateDatabase`,
        method: 'POST'
    })
}

export function updateDatabase2API() {
    return axios({
        url: `${api.dbPre}/updateDatabase2`,
        method: 'POST'
    })
}