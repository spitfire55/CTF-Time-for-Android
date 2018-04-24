package re.spitfy.ctftime.data.mapper

import re.spitfy.ctftime.data.Team
import re.spitfy.ctftime.data.db.entity.TeamEntity

fun List<Team>.toTeamEntities(): List<TeamEntity> = this.map {
    TeamEntity(
            id = it.id!!,
            academic = it.Academic,
            countryCode = it.CountryCode,
            description = it.Description,
            email = it.Email,
            icq = it.ICQ,
            jabber = it.Jabber,
            linkedin = it.LinkedIn,
            logo = it.Logo,
            name = it.Name,
            nameCaseInsensitive = it.NameCaseInsensitive,
            skype = it.Skype,
            telegram = it.Telegram,
            twitter = it.Twitter,
            website = it.Website
    )
}