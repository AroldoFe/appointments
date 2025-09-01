package br.com.aroldofe.appointments.service

import br.com.aroldofe.appointments.dto.bo.UserBO
import br.com.aroldofe.appointments.dto.request.UserRequest
import br.com.aroldofe.appointments.dto.request.UserUpdateRequest

interface UserService :
    NonAuthenticatedCreation<UserRequest, UserBO>,
    NonAuthenticatedUpdate<UserUpdateRequest, UserBO>