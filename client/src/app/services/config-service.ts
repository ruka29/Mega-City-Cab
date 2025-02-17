import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root',
})

export class ConfigService {
    private url = "http://localhost:8080/server_war_exploded/api";
    constructor(private http: HttpClient) {}

    
}