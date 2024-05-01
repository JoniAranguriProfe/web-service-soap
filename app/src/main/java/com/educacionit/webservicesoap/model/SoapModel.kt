package com.educacionit.webservicesoap.model

import com.educacionit.webservicesoap.contract.SoapContract
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE


class SoapModel : SoapContract.Model {

    override suspend fun getListOfLanguages(): List<Language> {
        val envelope = createSoapEnvelope()
        val httpTransport = HttpTransportSE(URL)
        return try {
            httpTransport.call(SOAP_ACTION, envelope)
            parseLanguages(envelope)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun parseLanguages(envelope: SoapSerializationEnvelope): List<Language> {
        val response = envelope.getResponse() as SoapObject
        val languages = mutableListOf<Language>()
        for (i in 0 until response.propertyCount) {
            val languageObj = response.getProperty(i) as SoapObject
            val isoCode = languageObj.getProperty("sISOCode")?.toString() ?: ""
            val name = languageObj.getProperty("sName")?.toString() ?: ""
            languages.add(Language(code = isoCode, name = name))
        }
        return languages
    }

    private fun createSoapEnvelope(): SoapSerializationEnvelope {
        val request = SoapObject(NAMESPACE, METHOD_NAME)
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.setOutputSoapObject(request)
        return envelope
    }

    fun parseSoapResponse(response: SoapObject): String {
        return response.getProperty(0).toString()
    }


    companion object {
        private const val NAMESPACE = "http://www.oorsprong.org/websamples.countryinfo"
        const val URL =
            "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso"
        const val SOAP_ACTION = "$NAMESPACE/ListOfLanguagesByName"
        private const val METHOD_NAME = "ListOfLanguagesByName"
    }
}
