import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ReportsComponent } from './pages/reports/reports.component';
import { ReportDetailComponent } from './pages/report-detail/report-detail.component';

export const routes: Routes = [
	{ path: '', component: HomeComponent, title: 'GamePatchForge | Home' },
	{ path: 'reports', component: ReportsComponent, title: 'GamePatchForge | Reports' },
	{ path: 'reports/:uuid', component: ReportDetailComponent, title: 'GamePatchForge | Report details' },
	{ path: '**', redirectTo: '' }
];
