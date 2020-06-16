import Cookies from 'js-cookie'

const TokenKey = 'NJUSE-TOKEN'

export function getToken() {
  return JSON.parse(sessionStorage.getItem(TokenKey))
}

export function setToken(token) {
  return sessionStorage.setItem(TokenKey, JSON.stringify(token))
}

export function removeToken() {
  return sessionStorage.removeItem(TokenKey)
}
