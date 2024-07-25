output = { input ->
    [
            contracts: input."Bestandsdaten oder Produktinfo Investment"."Vertrag".collect { contract ->
                [
                        "personDetail": contract."Partner".collect { partner ->
                            // Person part
                            [
                                    "pmPersonDetailDto": [
                                            "birthDate": partner."0100"."1"."Geburtsdatum",
                                            "name2": partner."0100"."1"."Name 2",
                                            "title": partner."0100"."1"."Titel"
                                    ],
                                    "pmPersonDto": [
                                            "firstName": partner."0100"."1"."Name 3",
                                            "lastName": partner."0100"."1"."Name 1"
                                    ],
                                    "adAddressDto": [
                                            "postalCode": partner."0100"."1"."Postleitzahl",
                                            "city": partner."0100"."1"."Ort",
                                            "street": partner."0100"."1"."Stra�e"
                                    ]
                            ]
                        },

                        // Contract part
                        "cmContractDto"     : [
                                "contractIdentifier": contract."0200"."1"."Satzanfang"."Versicherungsschein-Nummer",
                                "startDate"         : contract."0200"."1"."Vertragsbeginn",
                                "plannedEndDate"    : contract."0200"."1"."Vertragsablauf"
                        ],
                        "cmContractPriceDto": [
                                "priceNet" : contract."0200"."1"."Gesamtbeitrag (Netto) in W�hrungseinheiten",
                                "currency" : contract."0200"."1"."W�hrungsschl�ssel",
                                "startDate": contract."0200"."1"."Antragsdatum"
                        ]
                ]
            }
    ]
}