package com.sobarna.smartcalculatorapp.utils

import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import com.sobarna.smartcalculatorapp.domain.model.Calculator

object DataMapper {

    private fun mapperEntityToDomain(entity: CalculatorEntity): Calculator {
        return Calculator(
            entity.id,
            entity.input ?: "",
            entity.output?: 0.0
        )
    }

    fun mapperListEntityToListDomain(entity: List<CalculatorEntity>): List<Calculator> {
        val domainList: ArrayList<Calculator> = ArrayList()

        entity.forEach {
            mapperEntityToDomain(it).let { calculator ->
                domainList.add(calculator)
            }
        }
        return domainList
    }

    fun mapperDomainToEntity(domain: Calculator): CalculatorEntity {
        return CalculatorEntity(
            input = domain.input,
            output = domain.output
        )
    }
}